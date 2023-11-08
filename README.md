# BLOG

A blog for sharing posts, projects, and knowledge.

## Features

* Create, edit, and delete blog posts
* Code highlighting for technical content
* Pagination for post listing
* Categorize posts

## Tech Stack

- Front-end: HTML, CSS, JavaScript
- Back-end: Java Spring Boot
- Database: Postgres

## Task

Come up with a complete solution including frontend app and backend APIs for the task. Design the API endpoints needed
to make the app work and develop the UI.

## Frontend

The frontend of the application is built using React. It includes the following features:

* **Create a New Blog Post:** The application provides a form to create new blog posts. The form includes fields for the
post's title, content, and optional categories.

* **List of All Blog Posts:** The main area of the application displays a list of all blog posts. Each blog post in the list
shows the post's title, publication date, and a brief excerpt or summary.

* **View Blog Post Details:** When a blog post in the list is clicked, the application shows the full view of the blog post.
The full view includes the post's title, content, publication date, and categories.

* **Edit or Delete Blog Post:** The edit option allows users to update the post's title, content, and categories. The delete option allows users to remove
the blog post from the system.

## Backend

The backend of the application is built using Java Spring Boot. It includes the following features:

Here are the API endpoints:

1. [ ] Create a new blog post: 'POST /blog/post/create'
2. [ ] Get all posts: 'GET /blog/post'
3. [ ] Update a post by ID: 'PUT /blog/post/update/{id}'
4. [ ] Delete a post by ID: 'DELETE /blog/post/{id}'
5. [ ] Get posts by category: 'GET /blog/post/categories/{category}'
6. [ ] Get post by ID: 'GET /blog/post/{id}'

## Integration

Once the frontend and backend are developed, they are integrated together. The frontend makes HTTP requests to the
backend's API endpoints to create, retrieve, update, and delete blog posts. The backend responds to these requests with
the appropriate data.

## Code Quality

Ensure front end uses Redux/RTK with proper state management and ensure front end code quality. Ensure the API meets coding standards.

This is a high-level overview of how the application works. For more detailed information, please refer to the source code and comments.
