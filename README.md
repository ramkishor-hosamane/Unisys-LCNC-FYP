# Unisys Low-code/No-code Developement

## Project overview
> blah blah blah blah 

## Installation in Local Environment
> 

### 1. Clone the git repository to your workspace
```bash
git clone "https://github.com/ramkishor-hosamane/Unisys-LCNC-FYP.git"

```

### 2.Installing  dependencies
The project consists of three kind of applications Skyve (low-code/no-code platform) applications,Ecommerce front end (Angular) application and Governance application (python-flask).Procedure to Install dependencies for these application is as follows 

#### Skyve applications
Skyve requires following dependencies to install
* Java 11+ JDK
* Eclipse
* JBoss Wildfly 20

The Skyve applications in this project are ```ecommerce``` and ```fulfillment```.To Develop these applications in eclipse follow the steps given in link below
##### Demo of creating and  importing sample skyve application and running it 


**Steps -** https://docs.google.com/document/d/1fZNL7GOBxZpmRimBjUy7_pX0YjeS5VZp7Gd35Q7oCsU/edit?usp=sharing

[![Watch the video](https://drive.google.com/thumbnail?id=1WQ6hGfcs2DjtUU7qOo-FeF2YxlZ9gRZ_)](https://drive.google.com/file/d/1-SJ1kld7DrY7pUtR4Gfp2l2XVWMF3oiI/view?usp=sharing)

For this project ```content``` folder is already exists in the Repository . Paths in ```<project-name>.json``` and 
```<project-name>-ds.xml``` needs to be updated accordingly.

#### Ecommerce Angular Application 
It requires following dependencies to install
* [Nodejs](https://nodejs.org/en/)
* Angular
    ```bash
    #Run the follwing command in propmt or terminal to install angular
    npm install -g @angular/cli
    #Go to the project Repository in your workspace via command propmt or terminal 
    cd Ecommerce-Client
    #install the project dependecies by running following command
    npm install --save-dev @angular-devkit/build-angular
    ```

#### Governance Flask  Application 
It requires following dependencies to install
* [Python 3.8](https://www.python.org/downloads/)
* Flask and other dependencies
    ```bash
    #Go to the project Repository in your workspace via command propmt or terminal 
    cd Governance-App
    #install the project dependecies by running following command
    pip install requirements.txt
    ```


**Note:** Python3.8 comes with pip installed.If you dont have pip follow the steps from the below reference document based on your Operating System.
Reference: [https://docs.python-guide.org/starting/installation/](https://docs.python-guide.org/starting/installation/)


### 3.Running the Project
> There are two applications which need to be executed - ```Governance-App``` and ```Ecommerce-Client```
> #### 1.Governance app
> Before running the application Do the following steps 

* Goto ```uploads``` folder in ```Governance-App``` folder
* Update ```.json ``` and ```.ds-xml``` file according to your system
* Goto ```Governance-App``` folder
* Update ```paths.json ``` file according to your system paths
  ```json
    {
    "JAVA_HOME": "<path-to-Java>/",
    "UPLOADS_FOLDER":"<path-to-Unisys-Governance-App/uploads>/",
    "WILDFLY_HOME": "<path-to-wildfly-20.0.0.Final>/"
    }
  ```
#### follow the steps to run the application 
```bash
#Go to the project Repository in your workspace via command propmt or terminal 
cd Governance-App
#Run the governance app by following command
python app.py
```
Try opening [http://localhost:5000](http://localhost:5000) in the browser.
Now you are good to go.

> #### 2.Ecommerce-Client

```bash
#Go to the project Repository in your workspace via command propmt or terminal 
cd Ecommerce-Client
#Run the Ecommerce app by following command
ng serve
```
Try opening [http://localhost:4200](http://localhost:4200) in the browser.
Now you are good to go.


----
### Following things are not updated yet
#### 6. Demo Video link
https://drive.google.com/fle/d/1wpw4_vFaHaC5ARJhGeFO7iU0qfos_P45/viewusp=shari

#### 7. URLs and screenshots
#### Home page: [http://localhost:8000/home](http://localhost:8000/home)
![](https://i.imgur.com/yZV7741.jpg)
#### About: [http://localhost:8000/about](http://localhost:8000/about)
![](https://i.imgur.com/WdfFPEv.jpg)
#### Scrapping: [http://localhost:8000/scrapping](http://localhost:8000/scrapping)
![](https://i.imgur.com/FKYRB49.jpg)
#### Analysis: [http://localhost:8000/analysis](http://localhost:8000/analysis)
![](https://i.imgur.com/cx746KF.jpg)

