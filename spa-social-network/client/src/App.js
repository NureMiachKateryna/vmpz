import React, { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [users, setUsers] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState(null);
  const [posts, setPosts] = useState([]);
  const [newPostText, setNewPostText] = useState("");
  const [newComment, setNewComment] = useState({});

  // Завантаження користувачів
  useEffect(() => {
    axios.get("http://localhost:3001/api/users")
      .then(res => setUsers(res.data));
  }, []);

  // Завантаження постів користувача
  const loadPosts = (userId) => {
    setSelectedUserId(userId);
    axios.get(`http://localhost:3001/api/users/${userId}/posts`)
      .then(res => setPosts(res.data));
  };

  // Створення поста
  const createPost = () => {
    if (!newPostText.trim()) return;
    axios.post(`http://localhost:3001/api/users/${selectedUserId}/posts`, {
      content: newPostText
    }).then(res => {
      setPosts([res.data, ...posts]);
      setNewPostText("");
    });
  };

  // Додавання коментаря
  const addComment = (postId) => {
    const commentData = newComment[postId];
    if (!commentData?.text || !commentData?.authorId) return;

    axios.post(`http://localhost:3001/api/posts/${postId}/comments`, {
      authorId: commentData.authorId,
      text: commentData.text,
    }).then(res => {
      const updatedPosts = posts.map(p =>
        p.id === postId
          ? { ...p, comments: [...(p.comments || []), res.data] }
          : p
      );
      setPosts(updatedPosts);
      setNewComment({ ...newComment, [postId]: {} });
    });
  };

  // Додавання в друзі
  const addFriend = (friendId) => {
    const updatedUsers = users.map(u => {
      if (u.id === selectedUserId && !u.friends.includes(friendId)) {
        return { ...u, friends: [...u.friends, friendId] };
      }
      return u;
    });
    setUsers(updatedUsers);
  };

  const selectedUser = users.find(u => u.id === selectedUserId);
  const userFriends = selectedUser?.friends?.map(id => users.find(u => u.id === id)) || [];

  return (
    <div className="container">
      <h1>Соціальна Мережа</h1>
      <div className="main">
        <div className="sidebar">
          <h2>Користувачі</h2>
          <ul>
            {users.map(user => (
              <li key={user.id}>
                <button
                  className={user.id === selectedUserId ? "active" : ""}
                  onClick={() => loadPosts(user.id)}
                >
                  {user.name}
                </button>
              </li>
            ))}
          </ul>

          {userFriends.length > 0 && (
            <>
              <h3>Друзі:</h3>
              <ul>
                {userFriends.map(f =>
                  f ? <li key={f.id}>{f.name}</li> : null
                )}
              </ul>
            </>
          )}

          <h3>Додати в друзі:</h3>
          <ul>
            {users
              .filter(u => u.id !== selectedUserId && !selectedUser?.friends?.includes(u.id))
              .map(u => (
                <li key={u.id}>
                  {u.name}
                  <button onClick={() => addFriend(u.id)}>Додати</button>
                </li>
              ))}
          </ul>
        </div>

        <div className="content">
          {selectedUserId ? (
            <>
              <h2>Пости {selectedUser?.name}</h2>

              <div className="new-post">
                <textarea
                  rows="3"
                  placeholder="Що нового?"
                  value={newPostText}
                  onChange={(e) => setNewPostText(e.target.value)}
                />
                <button onClick={createPost}>Опублікувати</button>
              </div>

              {posts.map(post => (
                <div key={post.id} className="post">
                  <p>{post.content}</p>
                  <div className="comments">
                    <strong>Коментарі:</strong>
                    <ul>
                      {post.comments?.map(c => {
                        const author = users.find(u => u.id === c.authorId);
                        return (
                          <li key={c.id}>
                            <b>{author?.name || "?"}:</b> {c.text}
                          </li>
                        );
                      })}
                    </ul>

                    <div className="new-comment">
                      <select
                        value={newComment[post.id]?.authorId || ""}
                        onChange={(e) =>
                          setNewComment({
                            ...newComment,
                            [post.id]: {
                              ...newComment[post.id],
                              authorId: parseInt(e.target.value),
                            },
                          })
                        }
                      >
                        <option value="">Вибери користувача</option>
                        {users.map(u => (
                          <option key={u.id} value={u.id}>{u.name}</option>
                        ))}
                      </select>

                      <input
                        type="text"
                        placeholder="Напиши коментар..."
                        value={newComment[post.id]?.text || ""}
                        onChange={(e) =>
                          setNewComment({
                            ...newComment,
                            [post.id]: {
                              ...newComment[post.id],
                              text: e.target.value,
                            },
                          })
                        }
                      />
                      <button onClick={() => addComment(post.id)}>💬</button>
                    </div>
                  </div>
                </div>
              ))}
            </>
          ) : (
            <p className="hint">Вибери користувача, щоб побачити пости і друзів</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
