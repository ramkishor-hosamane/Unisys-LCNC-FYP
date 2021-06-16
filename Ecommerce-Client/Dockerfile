FROM node:16

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY . /app


COPY package.json /app/package.json
#RUN pip install -r requirements.txt

RUN npm install @angular/cli && npm install
#RUN npm install -g @angular/cli@10.2.0

CMD npm start
