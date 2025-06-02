const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const { users, posts } = require("./data");

const app = express();
app.use(cors());
app.use(bodyParser.json());

// Отримати всіх користувачів
app.get("/api/users", (req, res) => {
  res.json(users);
});

// Отримати пости користувача
app.get("/api/users/:id/posts", (req, res) => {
  const userId = parseInt(req.params.id);
  const userPosts = posts.filter(post => post.userId === userId);
  res.json(userPosts);
});

// Створити новий пост
app.post("/api/users/:id/posts", (req, res) => {
  const userId = parseInt(req.params.id);
  const newPost = {
    id: posts.length + 1,
    userId,
    content: req.body.content,
    comments: []
  };
  posts.push(newPost);
  res.status(201).json(newPost);
});

// Додати коментар до поста
app.post("/api/posts/:postId/comments", (req, res) => {
  const postId = parseInt(req.params.postId);
  const post = posts.find(p => p.id === postId);
  if (!post) return res.status(404).json({ error: "Post not found" });

  const newComment = {
    id: post.comments.length + 1,
    authorId: req.body.authorId,
    text: req.body.text
  };

  post.comments.push(newComment);
  res.status(201).json(newComment);
});

app.listen(3001, () => {
  console.log("Сервер запущено на http://localhost:3001");
});
