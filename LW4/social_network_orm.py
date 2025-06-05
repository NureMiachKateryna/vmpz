from sqlalchemy import create_engine, Column, Integer, String, ForeignKey, DateTime, Text
from sqlalchemy.orm import declarative_base, relationship, sessionmaker
from datetime import datetime

Base = declarative_base()

# Користувач
class User(Base):
    __tablename__ = 'users'
    id = Column(Integer, primary_key=True)
    name = Column(String, nullable=False)
    email = Column(String, nullable=False, unique=True)

    posts = relationship("Post", back_populates="author")
    comments = relationship("Comment", back_populates="author")

# Пост
class Post(Base):
    __tablename__ = 'posts'
    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey('users.id'), nullable=False)
    content = Column(Text, nullable=False)
    timestamp = Column(DateTime, default=datetime.utcnow)

    author = relationship("User", back_populates="posts")
    comments = relationship("Comment", back_populates="post")

# Коментар
class Comment(Base):
    __tablename__ = 'comments'
    id = Column(Integer, primary_key=True)
    post_id = Column(Integer, ForeignKey('posts.id'), nullable=False)
    user_id = Column(Integer, ForeignKey('users.id'), nullable=False)
    content = Column(Text, nullable=False)
    timestamp = Column(DateTime, default=datetime.utcnow)

    post = relationship("Post", back_populates="comments")
    author = relationship("User", back_populates="comments")

# Підключення до БД
engine = create_engine('sqlite:///social_network.db')
Base.metadata.drop_all(engine)  # очистити попередню БД
Base.metadata.create_all(engine)
Session = sessionmaker(bind=engine)
session = Session()

# Додати пост
def add_post(user_id, content):
    post = Post(user_id=user_id, content=content)
    session.add(post)
    session.commit()
    print(f"Пост додано: {post.content}")

# Додати коментар
def add_comment(post_id, user_id, content):
    comment = Comment(post_id=post_id, user_id=user_id, content=content)
    session.add(comment)
    session.commit()
    print(f"Коментар додано: {comment.content}")

# Показати пости з коментарями
def show_posts_with_comments():
    posts = session.query(Post).all()
    for post in posts:
        print(f"\n📝 Пост #{post.id} від {post.author.name}: {post.content}")
        print(f"   Опубліковано: {post.timestamp.strftime('%Y-%m-%d %H:%M')}")
        if post.comments:
            for comment in post.comments:
                print(f"   💬 {comment.author.name}: {comment.content}")
        else:
            print("   (Немає коментарів)")

# --- Основна логіка ---
if __name__ == "__main__":
    # Додаємо двох користувачів
    katya = User(name="Катя", email="katya@example.com")
    alex = User(name="Олексій", email="alex@example.com")
    session.add_all([katya, alex])
    session.commit()

    # Катя створює пост
    add_post(user_id=katya.id, content="Нарешті побувала у Львові! Атмосфера неймовірна ☕🏰")

    # Олексій залишає коментар
    add_comment(post_id=1, user_id=alex.id, content="Дуже люблю Львів! Відвідай Шевченківський гай 😊")

    # Показати всі пости з коментарями
    show_posts_with_comments()
