import React, { useState } from "react";

function AddStory({ onAdd }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const submitHandler = (e) => {
    e.preventDefault();
    if (!title || !content) return;
    onAdd({ title, content });
    setTitle("");
    setContent("");
  };

  return (
    <form onSubmit={submitHandler} className="card">
      <h2>Add Story</h2>
      <input
        type="text"
        placeholder="Story Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <textarea
        placeholder="Write your story..."
        value={content}
        onChange={(e) => setContent(e.target.value)}
        rows={5}
        required
      />
      <button type="submit">Post</button>
    </form>
  );
}

export default AddStory;
