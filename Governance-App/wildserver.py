from utils import *
from DataBase import *
from distutils.dir_util import copy_tree
import shutil
import subprocess
paths = load_json_file("paths.json")
paths["WILDFLY_BIN"] = paths["WILDFLY_HOME"] + "bin/"
paths["WILDFLY_DEPLOYMENTS"] = paths["WILDFLY_HOME"] + "standalone/deployments/"

#print(paths)
def start_server():
    dir_src = paths['WILDFLY_DEPLOYMENTS']

    wildpath = paths['WILDFLY_BIN']
    print(wildpath)
    print ("Wildfly started successfully")
    #url = 'http://localhost:8080/'+project_war
    if isLinux():
        #subprocess.Popen(["bash",f"{wildpath}/standalone.sh"])
        #subprocess.check_output(["bash",f"{wildpath}/standalone.sh"])
        print()
        os.system(f"{wildpath}standalone.sh")
        #webbrowser.open(url)
    else:    
        #webbrowser.register('chrome', None,webbrowser.BackgroundBrowser("C://Program Files (x86)//Google//Chrome//Application//chrome.exe"))
        #webbrowser.get('chrome').open(url)
        os.system(f'{wildpath}standalone.bat')






def restart_server(deleted_project=None):

    wildpath = paths['WILDFLY_BIN']
    #os.chdir(paths['WILDFLY_BIN'])
    #print(os.getcwd())
    if isLinux():
        os.system(f'{wildpath}jboss-cli.sh --connect command=:reload')
    else:
        os.system(f'{wildpath}jboss-cli.bat --connect command=:reload')
    #Remove unwanted deleted project contents
    if deleted_project:
        del_dodeploy_path=paths['WILDFLY_DEPLOYMENTS']+deleted_project+".war.deployed"
        del_undeploy_path=paths['WILDFLY_DEPLOYMENTS']+deleted_project+"-ds.xml.undeployed"
        try:
            os.remove(del_dodeploy_path)
            os.remove(del_undeploy_path)    
        
        except Exception as e:
            print("Exception in removing "+e)
            pass
    print ("wildfly restarted successfully")




def TurnOn(project,can_restart=True):
    project_war=project+'.war'
    project_json=project+'.json'
    project_ds=project+'-ds.xml'
    src_path=paths['UPLOADS_FOLDER']+project_war
    src_dodeploy_path=paths['UPLOADS_FOLDER']+project_war+".dodeploy"

    src_json=paths['UPLOADS_FOLDER']+project_json
    src_ds=paths['UPLOADS_FOLDER']+project_ds
    d_path=paths['WILDFLY_DEPLOYMENTS']
    # del_path="C:/Users/Dell/wildfly-20.0.0.Final/standalone/deployments/"+project_war
    del_path=paths['WILDFLY_DEPLOYMENTS']+project_war
    del_json=d_path+project_json
    del_ds=d_path+project_ds
    print("Came here")
    shutil.copy(src_json,d_path)
    shutil.copy(src_ds,d_path)
    shutil.copy(src_dodeploy_path,d_path)    
    try:
        copy_tree(src_path, d_path+project_war)
    except Exception as e:
        print("Exception occured while copying war")
        print(e)


    print("Copied")
    if can_restart:
        restart_server()   

def TurnOff(project):
    project_war=project+'.war'
    project_json=project+'.json'
    project_ds=project+'-ds.xml'
    d_path=paths['WILDFLY_DEPLOYMENTS']
    # del_path="C:/Users/Dell/wildfly-20.0.0.Final/standalone/deployments/"+project_war
    del_path=paths['WILDFLY_DEPLOYMENTS']+project_war

    del_json=d_path+project_json
    del_ds=d_path+project_ds
    print(del_path)
    shutil.rmtree(del_path,ignore_errors=True)
    os.remove(del_ds)
    os.remove(del_json)


    #os.getcwd()
    restart_server(project) 


def start_all_projects():
    #project_list = Project.query.all()
    #for project in project_list:
    #    TurnOn(project.project_war,False)
    start_server()




'''
    
def navigate_and_renamejson(src,project_war):
new_json=project_war+'.json'
for item in os.listdir(src):
    s = os.path.join(src, item)      
    if s.endswith(".json"):
        shutil.copy(s, os.path.join(src, new_json))
with open(new_json, 'r') as file :
    filedata = file.read()
filedata = filedata.replace('ecommerce', project_war)
with open(new_json, 'w') as file:
    file.write(filedata)

def navigate_and_renameds(src,project_war):
new_ds=project_war+'-ds.xml'
for item in os.listdir(src):
    s = os.path.join(src, item)
    if s.endswith("-ds.xml"):
        shutil.copy(s, os.path.join(src, new_ds))
with open(new_ds, 'r') as file :
    filedata = file.read()
filedata = filedata.replace('ecommerce', project_war)
with open(new_ds, 'w') as file:
    file.write(filedata)



'''