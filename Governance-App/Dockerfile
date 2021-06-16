FROM ubuntu:latest
#FROM python:alpine3.7

COPY . /app

WORKDIR /app


RUN ./install_dependencies.sh

RUN ./configs.sh

RUN pip install --upgrade pip

RUN pip install -r requirements.txt

EXPOSE 5000

CMD ["python3","app.py" ,"--host=0.0.0.0"]