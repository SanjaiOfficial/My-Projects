import React, { useState, useEffect } from "react";
import AddStory from "./components/AddStory";
import StoryList from "./components/StoryList";
import StoryView from "./components/StoryView";
import { saveAs } from "file-saver";

function downloadStories(stories) {
  const blob = new Blob([JSON.stringify(stories, null, 2)], { type: "application/json" });
  saveAs(blob, "my_stories.json");
}

function App() {
  const [stories, setStories] = useState([]);
  const [selectedStory, setSelectedStory] = useState(null);

  useEffect(() => {
    const storedStories = JSON.parse(localStorage.getItem("stories")) || [];
    setStories(storedStories);
  }, []);

  const saveStories = (updatedStories) => {
    localStorage.setItem("stories", JSON.stringify(updatedStories));
    setStories(updatedStories);
  };

  const addStory = (story) => {
    const updated = [...stories, story];
    saveStories(updated);
  };

  const deleteStory = (index) => {
    const updated = stories.filter((_, i) => i !== index);
    saveStories(updated);
  };

  return (
    <div className="container">
      <h1>📚 Mini Wattpad</h1>
      {selectedStory ? (
        <StoryView story={selectedStory} onBack={() => setSelectedStory(null)} />
      ) : (
        <>
          <AddStory onAdd={addStory} />
          <StoryList stories={stories} onView={setSelectedStory} onDelete={deleteStory} />
          <button onClick={() => downloadStories(stories)}>Download All Stories</button>
        </>
      )}
    </div>
  );
}

export default App;
