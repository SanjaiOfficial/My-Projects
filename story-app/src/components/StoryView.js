import React from "react";

function StoryView({ story, onBack }) {
  return (
    <div className="card">
      <h2>{story.title}</h2>
      <p>{story.content}</p>
      <button onClick={onBack}>Back</button>
    </div>
  );
}

export default StoryView;
