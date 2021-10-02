- Front-end web must be deployed to nginx webhost
- Nginx must serve web content & use reverse proxy to resolve CORS issues
- Backend to be accessible by nginx webhost via docker network

Docker containers:

    Web Host
    - nginx
    - Serve web content
    - Reverse proxy pointing localhost to backend docker containers
    - Externally accesible port for web traffic 

    Back end
    - spin up docker containers for backend
    - must be on the same network as the web host
