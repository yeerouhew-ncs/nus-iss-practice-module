# Fetching the latest node image on apline linux
FROM node:20-alpine as build

# Setting up the work directory
WORKDIR /app

# Installing dependencies
COPY ./package.json ./yarn.lock ./
RUN yarn

# Copying all the files in our project
COPY . .

# To increase nodejs memory limit to 4GB
ENV NODE_OPTIONS="--max-old-space-size=4096"

# Building our application
RUN yarn run build

# Fetching the latest nginx image
FROM nginx

# Copying built assets from build
COPY --from=build /app/build /usr/share/nginx/html

# Copying our nginx.conf
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

