import os
from sys import audit
import requests
import shutil
import time
from distutils.dir_util import copy_tree
from requests.auth import HTTPBasicAuth
from flask import Flask, redirect, url_for, render_template, request, session, flash
from sqlalchemy import DateTime
#from werkzeug import secure_filename, FileStorage
from werkzeug.utils import secure_filename
from werkzeug.datastructures import  FileStorage
from werkzeug.datastructures import  MultiDict
from datetime import datetime, date
from flask_sqlalchemy import SQLAlchemy
from flask_socketio import SocketIO, send, emit
from flask_restful import Resource, Api
import random
import json
import subprocess
import webbrowser
import patoolib
from pyunpack import Archive
from flask_cors import CORS
from utils import *
import subprocess
from threading import Thread
import pandas as pd
from flask import send_file
paths = load_json_file("paths.json")
paths["WILDFLY_BIN"] = paths["WILDFLY_HOME"] + "bin/"
paths["WILDFLY_DEPLOYMENTS"] = paths["WILDFLY_HOME"] + "standalone/deployments/"


#App initialization
app = Flask(__name__)
app.secret_key = "hello"
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///users.sqlite3'
app.config['DEBUG'] = True
socketio = SocketIO(app)
api = Api(app)
db = SQLAlchemy(app)
CORS(app)
path = os.getcwd()
UPLOAD_FOLDER = os.path.join(path, 'uploads')


# Make directory if uploads is not exists
if not os.path.isdir(UPLOAD_FOLDER):
    os.mkdir(UPLOAD_FOLDER)

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
from DataBase import *


current_app_name = None


def log_governance_audit(Appname,operation):
    timestamp = str(date.today().strftime("%d/%m/%Y"))+"  "+str(datetime.now())[11:19]
    audit = GovernanceAudit(AppName=Appname, operation=operation,userName='admin',timestamp=timestamp)
    db.session.add(audit)
    db.session.commit()  
    print("Loged audit")  


class AuditApi(Resource):

    def get(self):
        return {'message': 'hello world'}

    def post(self):
        global current_app_name
        print("Post came")
        print()
        data = request.get_json()
        print(data)
        print("BYE")
        operation=data['operation']
        if operation == 'I':
            data['operation'] = 'Insert'
        elif operation == 'U':
            data['operation'] = 'Update'
        elif operation == 'D':
            data['operation'] = 'Delete'
        

        project_name = data['bizDataGroupId']
        print(project_name)
        project = Project.query.filter_by(project_name=project_name).first()
        print(project)
        
        audit = ProjectAudit(auditModuleName=data['auditModuleName'],
            auditDocumentName=data['auditDocumentName'],
            operation=data['operation'],
            userName = data['userName'],
            timestamp = data['timestamp'],proj=project)

        db.session.add(audit)
        db.session.commit()
        
        
        if(current_app_name==project_name):
            socketio.emit("audit",data,broadcast=True)
        else:
            print("Not sending")
        #Save the data in database
        #data = {'message': 'hello world'}
        return data
api.add_resource(AuditApi, '/newaudit')

@socketio.on('connect')
def ws_connect():
    print("Connected")


@app.route("/")
def index():
    return redirect("/login")


@app.route("/login", methods=["GET", "POST"])
def login():
    error = None
    if request.method == 'POST':
        '''username = request.form['username']
        password = request.form['password']
        user = User(username, password)
        db.session.add(user)
        db.session.commit()'''
        if request.form['username'] != 'admin' or request.form['password'] != 'admin':
            error = 'Invalid Credentials. Please try again.'
            
            # print(error)
        else:
            return redirect(url_for('home'))
    log_governance_audit("Governance App","Logged in")

    return render_template("login.html", error=error)


@app.route("/home")
def home():
    total_projects = len(Project.query.all())
    total_audits = sum([len(project.audits) for project in Project.query.all()])
    running_apps = 0
    application_status = session["application_status"]
    for project in Project.query.all():
        if(application_status[project.project_name] =="true"):
            running_apps+=1 
    return render_template("home.html",total_projects=total_projects,total_audits=total_audits,running_apps=running_apps)





@app.route("/updatepowerstatus",methods=['POST', 'GET'])
def updatepowerstatus():
    print("Came here")
    status = request.form['switch_status']
    project_name = request.form['project']
    print(project_name,status)
    application_status = session["application_status"]
    status = application_status[project_name]
    # status=True
    print("Actual project status ",status)
    if status =="false": 
        # os.mkdir(project_name)      
        TurnOn(project_name)
        application_status[project_name]= "true"
        log_governance_audit(project_name,"Turned on")
        print("project turned on")
    elif status=="true":
        TurnOff(project_name)
        print("project turned off")
        application_status[project_name]= "false"
        log_governance_audit(project_name,"Turned off")

    else:
        print("Not found")
        return
    session["application_status"] = application_status
    print(session)

    #return redirect("/viewapplications")
    print(session["application_status"])
    return json.dumps({'msg':'ok','current_status':not status})




# @app.route("/deleteaudit",methods=['POST', 'GET'])
# def deleteaudit():
#     auditno = request.form['auditno']
#     print(auditno)
#     project_name = request.form['project']
#     if(project_name == "governance"):
#         audit_obj = GovernanceAudit.query.filter_by(auditid=int(auditno)).first()
#         db.session.delete(audit_obj)
#         db.session.commit()

#     else:
#         project = Project.query.filter_by(project_name=project_name).first()
#         audit_list = reversed(project.audits)

#     return json.dumps({})










def make_report(project_name):
    if(project_name == "governance"):
        audit_list = list(GovernanceAudit.query.all())
    else:
        project = Project.query.filter_by(project_name=project_name).first()
        audit_list = project.audits
        print(audit_list)        
    row = audit_list[0]
    report_df = {column: [] for column in row.__dict__.keys()}
    print(report_df)
    for audit in audit_list:
        audit_dict = audit.__dict__
        for column in audit_dict.keys():
            report_df[column].append(audit_dict[column])
    print(report_df)
    #report_df = {}
    filepath = f"{paths['UPLOADS_FOLDER']}{project_name}.xlsx"
    report_df = pd.DataFrame(report_df)
    writer = pd.ExcelWriter(filepath, engine='xlsxwriter')
    report_df.to_excel(writer,sheet_name=project_name, index = False)
    writer.save()          
    return filepath

@app.route("/downloadreport/<pname>",methods=['POST', 'GET'])
def downloadreport(pname):
    print("Came there")
    #is_governance = request.form['is_governance']
    project_name = pname
    #if project_name=="governance":
    path = make_report(project_name)
    #
    #return redirect("/viewapplications")
    return send_file(path, as_attachment=True)


# @app.route("/deleteallaudits/<pname>",methods=['POST', 'GET'])
# def deleteallaudits(pname):
#     print("Came there")
#     #is_governance = request.form['is_governance']
#     project_name = pname
#     if(project_name == "governance"):
#         try:
#             num_rows_deleted = db.session.query(GovernanceAudit).delete()
#             db.session.commit()
#         except:
#             db.session.rollback()
#         return redirect(url_for("GovernanceAudit"))

#     else:
#         project = Project.query.filter_by(project_name=project_name).first()




@app.route("/newapplication", methods=["GET", "POST"])
def newapplication():
    if request.method == 'POST':
        project_name = request.form['project_name']
        print(project_name)
        database_dialect = request.form['database_dialect']
        print(database_dialect)

        if request.method =='POST':
            file = request.files['file']
        print (file)
        filename = secure_filename(file.filename)
        file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
        os.chdir(UPLOAD_FOLDER)
        os.getcwd()
        #os.mkdir(filename)
        patoolib.extract_archive(filename, outdir=paths['UPLOADS_FOLDER'])       
        print('File uploaded successfully!')

        
        
        
        project = Project(project_name, database_dialect,
                          file)
        db.session.add(project)
        db.session.commit()
        log_governance_audit(project_name,"Registered")



        flash("Project created succesfully")
        #return redirect(request.url)
        return render_template("newApplication.html",Alert="Project created succesfully",Alert_type="success")
    return render_template("newApplication.html")


@app.route("/viewapplications", methods=["GET", "POST"])
def viewapplications():

    #Testing 
    
    application_status = session["application_status"]
    print("Application status is ",session["application_status"])
    project_list = Project.query.all()
    return render_template("viewApplications.html", project_list=project_list,application_status=application_status)
    #values = userDetails.query.all()


@app.route("/applicationinfo/<pname>")
def applicationinfo(pname):
    global current_app_name
    print(pname)
    current_app_name = pname

    project = Project.query.filter_by(project_name=pname).first()
    project_audits = reversed(project.audits) 
    return render_template("applicationinfo.html", project=project,project_audits=project_audits,total_audits=len(project.audits))
    #values = userDetails.query.all()


@app.route("/viewaudits/Governance")
def GovernanceAudit():
    project_list = Project.query.all()
    governance_audits = reversed(GovernanceAudit.query.all())

    return render_template("viewGovAudits.html", project_list=project_list,governance_audits=governance_audits)

@app.route("/viewaudits/Applications")
def ApplicationsAudit():
    project_list = Project.query.all()
    return render_template("viewAppAudits.html", project_list=project_list)


@app.route("/viewaudits/Applications/<pname>")
def applicationaudit(pname):
    global current_app_name
    print(pname)
    current_app_name = pname

    project = Project.query.filter_by(project_name=pname).first()
    project_audits = reversed(project.audits) 
    return render_template("applicationaudit.html", project=project,project_audits=project_audits)





def start_wildfly_server():
    #for i,project in enumerate(Project.query.all()):
    #    data = {'num':i,'value':'true'}
    #    socketio.emit("switch",data,broadcast=True)
    start_all_projects()

@app.before_first_request
def activate_session():
    print("Initializwed session")
    application_status = {'ecommerce':'false','fulfillment':'false'}
    #for i,project in enumerate(Project.query.all()):
    #    application_status[project.project_name]="false"    
    session["application_status"] = application_status

if __name__ == "__main__":
    db.create_all()
    

    from wildserver import *
    #start_all_projects()
    wild_thread = Thread(target=start_server)
    wild_thread.start()


    socketio.run(app)

