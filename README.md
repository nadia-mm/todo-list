# Todo List API

This project provides a REST API for managing a todo list application. You can use this API to perform CRUD (Create, Read, Update, Delete) operations on todo items.

## Base URL

The base URL for the API is:


## Endpoints

### 1. **Get All Todos**

- **URL**: `/todos`
- **Method**: `GET`
- **Description**: Retrieves a list of all todo items.
- **Request**: No request body required.
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**:
      ```json
      [
        {
          "id": 1,
          "title": "Sample Todo",
          "description": "This is a sample todo item.",
          "isDone": false,
          "createdAt": "2024-08-03T12:00:00",
          "updatedAt": "2024-08-03T12:00:00"
        }
      ]
      ```

### 2. **Get Todo by ID**

- **URL**: `/todos/{id}`
- **Method**: `GET`
- **Description**: Retrieves a single todo item by its ID.
- **Parameters**:
    - `id` (path parameter): The ID of the todo item.
- **Request**: No request body required.
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**:
      ```json
      {
        "data": {
          "id": 1,
          "title": "Sample Todo",
          "description": "This is a sample todo item.",
          "isDone": false,
          "createdAt": "2024-08-03T12:00:00",
          "updatedAt": "2024-08-03T12:00:00"
        },
        "message": "Operation successful",
        "success": true
      }
      ```

### 3. **Create a Todo**

- **URL**: `/todos`
- **Method**: `POST`
- **Description**: Creates a new todo item.
- **Request**:
    - **Body**:
      ```json
      {
        "title": "New Todo",
        "description": "Description of the new todo.",
        "isDone": false
      }
      ```
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**:
      ```json
      {
        "data": {
          "id": 2,
          "title": "New Todo",
          "description": "Description of the new todo.",
          "isDone": false,
          "createdAt": "2024-08-03T12:01:00",
          "updatedAt": "2024-08-03T12:01:00"
        },
        "message": "Operation successful",
        "success": true
      }
      ```

### 4. **Update a Todo**

- **URL**: `/todos/{id}`
- **Method**: `PUT`
- **Description**: Updates an existing todo item by its ID.
- **Parameters**:
    - `id` (path parameter): The ID of the todo item.
- **Request**:
    - **Body**:
      ```json
      {
        "title": "Updated Todo",
        "description": "Updated description of the todo.",
        "isDone": true
      }
      ```
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**:
      ```json
      {
        "data": {
          "id": 1,
          "title": "Updated Todo",
          "description": "Updated description of the todo.",
          "isDone": true,
          "createdAt": "2024-08-03T12:00:00",
          "updatedAt": "2024-08-03T12:02:00"
        },
        "message": "Operation successful",
        "success": true
      }
      ```

### 5. **Delete a Todo**

- **URL**: `/todos/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a todo item by its ID.
- **Parameters**:
    - `id` (path parameter): The ID of the todo item.
- **Request**: No request body required.
- **Response**:
    - **Status Code**: `200 OK`
    - **Body**:
      ```json
      {
        "data": {
          "id": 1,
          "title": "Updated Todo",
          "description": "Updated description of the todo.",
          "isDone": true,
          "createdAt": "2024-08-03T12:00:00",
          "updatedAt": "2024-08-03T12:02:00"
        },
        "message": "Operation successful",
        "success": true
      }
      ```


### 6. **Filter Todos by status**

- **URL**: `/todos/filter?completed=false`
- **Method**: `GET`
- **Description**: Retrieves a list of all pending(false) or completed (true) todos.
- **Request**: No request body required.
  - **Response**:
      - **Status Code**: `200 OK`
        - **Body**:
          ```json
            [
                {
                "id": 1,
                "title": "Sample Todo",
                "description": "This is a sample todo item.",
                "isDone": false,
                "createdAt": "2024-08-03T12:00:00",
                "updatedAt": "2024-08-03T12:00:00"
                }
            ]
          ```

## Error Responses

In case of an error, the API will return a response with an appropriate error message:

- **Status Code**: `4xx` or `5xx`
- **Body**:
  ```json
  {
    "message": "Error message",
    "success": false
  }


## Running the application

 ```shell
git clone https://github.com/nadia-mm/todo-list.git
cd todo-list
mvn spring-boot:run

