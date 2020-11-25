# Sashimi

Simple RADIUS session simulator that runs as HTTP REST server to allow you easy interface for session operations

Sashimi based on the Cisco ISE PxGrid session simulator by [alei121](https://github.com/alei121)

## Installation

Best way to run Sashimi is in docker container.

Server runs on port `8080` by default, if running in container it can be forwarded into any port of host machine.

From the project directory, Run:
```
docker run --rm -it -p 8080:8080 $(docker build -q --no-cache .)
```
Sashimi also can run natively using attached maven scripts.

## Usage
Sashimi included Swagger documentation that avilable under following URL after running it:
```
http://<hostname>:<port>/swagger-ui/index.html?url=/v3/api-docs
```
Sashimi basic call to create session looks like
```
POST http://<hostname>:<port>/session
{
    "radiusAddress": <RADIUS_SERVER_ADDRESS>,
    "radiusSecret": <RADIUS_SERVER_SECRET",
    "username": <ENDPOINT_USERNAME>,
    "password": <ENDPOINT_PASSWORD>,
    "macAddress": <ENDPOINT_MAC_ADDRESS>
}
```

## Contributing
Sashimi developed for specific usage but we can expand it into many directions.

For example: 
- Delete / CoA sessions
- AV-pairs (such as Cisco Trustec attributes)
- More automated tests

Feel free to start your branch and contact gmanor@cisco.com to collaborate
