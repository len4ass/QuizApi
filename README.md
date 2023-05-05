# QuizApi
JService trivia wrapper with authorization

## How to use
1. Clone repository
2. Run [PostgreSQL in docker](https://hub.docker.com/_/postgres)
3. Build an application image from `Dockerfile`
    * Example: `docker build -t quiz_api -f Dockerfile .`
4. Run the app in docker
    * Example: `docker run --restart unless-stopped --name QUIZAPI -d -p EXTERNAL_PORT:8080 -e DB_NAME=... -e DB_HOST=... -e DB_PORT=... -e DB_USER=... -e DB_PASS=... -e JWT_SECRET=... quiz_api`
    * Environment variables: 
        * `EXTERNAL_PORT (required)` - a port you want to expose for application to be available at      
        * `DB_NAME (required)` - name of the database inside the container you ran in step 2 (default is `postgres`)
        * `DB_HOST (required)` - host where the database container is ran (if it's done locally, then it should be `localhost` or `host.docker.internal`)
        * `DB_PORT (required)` - port that is exposed on the host for database connections (usually it's 5432)
        * `DB_USER (required)` - user that you specified upon running database in container
        * `DB_PASS (required)` - password for the user
        * `JWT_SECRET (required)` - private key for JWT token generation, validation and claim extraction ([encryption key, at least 256 bits in HEX format](https://allkeysgenerator.com))
5. Use endpoints with Postman, Insomnia etc.

## Endpoints

### 1. `POST api/auth/login`
Authorizes an existing user and returns JWT token to be used in secured endpoints. If user doesn't exist or the password is invalid, response will be of 401 status code, otherwise 200.

#### Headers
1. Name: `Content-Type`, value: `application/json`

#### Body
```json
{
    "username":"string",
    "password":"string"
}
```

#### Response
```json
{
    "status": "string",
    "info": "string",
    "token": "string" 
}
```

### 2. `POST api/auth/signup`
Registers a new user and returns a JWT token to be used in secured endpoints. 
If such user already exists or username/password do not qualify to the constraints, response will be of 401 status code, otherwise 200.

#### Headers
1. Name: `Content-Type`, value: `application/json`

#### Body
```json
{
    "username":"string",
    "password":"string"
}
```

#### Response
```json
{
    "status": "string",
    "info": "string",
    "token": "string" 
}
```

### 3. `GET api/demo`
Returns `Hello!` for any authorized user.\
If an unauthorized client tries to accept the endpoint, the client will receive response of 403 status code, otherwise 200.

#### Headers
1. Name: `Authorization`, value: `Bearer ...` (put token you got after authorizing here)

#### Response
`Hello!`

### 4. `GET api/questions/get-random-question`
Gets a random question and assigns it to the user that's associated with the JWT token passed in authorization header.\
If a user has a question assigned already, will return the question with 208 status code. If an unauthorized client tries to accept the endpoint, the client will receive response of 403 status code.

#### Headers
1. Name: `Authorization`, value: `Bearer ...` (put token you got after authorizing here)

#### Response
```json
{
    "status": "string",
    "question": "string"
}
```

### 5. `POST api/answers/answer`

#### Description
Posts the answer to the pending question of the user that's associated with the JWT token passed in authorization header. 
If the user hasn't retrieved the question or trying to answer the question that they already sent answer to, they will be notified about incorrect action.\
A user will get 10 rating points if they answer the question correctly.\
If a user doesn't have a question assigned already, response with status code 400 will be sent back. If a user has already answered the question, response with status code 208 will be sent back. If an unauthorized client tries to accept the endpoint, the client will receive response of 403 status code, otherwise 200.


#### Headers
1. Name: `Authorization`, value: `Bearer ...` (put token you got after authorizing here)

#### Body
```json
{
    "answer": "string"
}
```

#### Response
```json
{
    "status": "string",
    "info": "string"
}
```

### 5. `GET api/rating/all`

#### Description
Gets rating of all users with positive rating value.\
If an unauthorized client tries to accept the endpoint, the client will receive response of 403 status code, otherwise 200.

#### Headers
1. Name: `Authorization`, value: `Bearer ...` (put token you got after authorizing here)

#### Response
```json
{
    "count": int,
    "users": list [
        {
            "username": "string",
            "rating": double,
            "correct_answers": int,
            "total_questions": int
        },
        ...
        {
            "username": "len4ass",
            "rating": double,
            "correct_answers": int,
            "total_questions": int
        }
    ]
}
```