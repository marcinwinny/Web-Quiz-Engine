# Web-Quiz-Engine
A simple engine for creating and solving quizzes through HTTP API.

H2 database was used to store all the data.

# Desription

API supports creating, getting, solving and deleting quizzes. Each quiz is described by id, title, text and array of correct answers. Answer is not visible in the API.

To perform any actions with quizzes user have to be registrater and then authorized via HTTP Basic Auth. Otherwise, the service returns the `HTTP 401 (Unauthorized)` code.

#Examples

### Register new user
```sh
POST: /api/register
```

### Create new quiz
```sh
POST: /api/quizzes
```

### Get quiz by id

```sh
GET: /api/quizzes/{id}
```

### Solve a quiz

```sh
GET: /api/quizzes/{id}/solve
```

### Delete quiz by id

Deleting quiz is only possible for user that created it.

```sh
DELETE: /api/quizzes/{id}
```

### Get all quizzes with paging

```sh
GET: /api/quizzes
```
Default value is 10 quizzes per page, starting from first page, sorted by id.

For getting 5 quizzes per page, page 2:

```sh
GET: /api/quizzes?size=5&page=2
```

### Get all completions of quizzed with paging

Shows all completions of quizzes by current user. For one quiz there can be multiple completions.

```sh
GET: /api/quizzes/completed
```





