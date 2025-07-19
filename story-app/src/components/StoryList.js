import React from "react";

function StoryList({ stories, onView, onDelete }) {
  return (
    <div className="card">
      <h2>All Stories</h2>
      {stories.length === 0 ? <p>No stories yet.</p> : null}
      {stories.map((story, index) => (
        <div key={index} className="story-item">
          <strong>{story.title}</strong>
          <div className="actions">
            <button onClick={() => onView(story)}>Read</button>
            <button onClick={() => onDelete(index)} className="delete">Delete</button>
          </div>
        </div>
      ))}
    </div>
  );
}

export default StoryList;
