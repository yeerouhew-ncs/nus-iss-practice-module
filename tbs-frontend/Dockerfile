# Fetching the latest node image on apline linux
FROM node:20-alpine as build

# Declaring env
# ENV NODE_ENV production

# Setting up the work directory
WORKDIR /app

RUN npm config set registry https://registry.npmjs.org/

# Installing dependencies
COPY package*.json ./
RUN npm install

# Copying all the files in our project
COPY --exclude=nginx/* . ./

# Building our application
RUN npm run build

# Fetching the latest nginx image
FROM nginx:1.25.4-alpine

# Copying built assets from builder
COPY --from=build /app/build /usr/share/nginx/html

# Copying our nginx.conf
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf