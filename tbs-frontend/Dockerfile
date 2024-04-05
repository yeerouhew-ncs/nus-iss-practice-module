# Fetching the latest node image on apline linux
FROM node:18-alpine as build

# Declaring env
# ENV NODE_ENV production

# Setting up the work directory
WORKDIR /app

# Installing dependencies
COPY ./package.json ./yarn.lock ./
RUN yarn

# Copying all the files in our project
COPY . .

# Building our application
RUN yarn run build

# Fetching the latest nginx image
FROM nginx

# Copying built assets from builder
COPY --from=build /app/build /usr/share/nginx/html

# Copying our nginx.conf
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

# # Stage 1:
# FROM node:14 as builder
# WORKDIR /app
# COPY package*.json ./ 
# RUN npm install
# COPY . .   
# RUN npm run build
 
# # Stage 2:
# FROM nginx:alpine
# COPY --from=builder /app/build /usr/share/nginx/html
# # COPY nginx.conf /etc/nginx/nginx.conf
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]