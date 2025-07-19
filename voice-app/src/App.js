import React, { useState, useRef } from 'react';
import './App.css';

const App = () => {
  const [mode, setMode] = useState('text-to-voice');
  const [text, setText] = useState('');
  const [transcript, setTranscript] = useState('');
  const [isRecording, setIsRecording] = useState(false);
  const [selectedVoice, setSelectedVoice] = useState('');
  const [voices, setVoices] = useState([]);
  const [audioUrl, setAudioUrl] = useState(null);
  const recognitionRef = useRef(null);

  // Load voices
  React.useEffect(() => {
    const synth = window.speechSynthesis;
    const populateVoices = () => {
      const availableVoices = synth.getVoices();
      setVoices(availableVoices);
      if (!selectedVoice && availableVoices.length > 0) {
        setSelectedVoice(availableVoices[0].name);
      }
    };
    populateVoices();
    if (synth.onvoiceschanged !== undefined) {
      synth.onvoiceschanged = populateVoices;
    }
  }, [selectedVoice]);

  const handleTextToSpeech = () => {
    if (text.trim() === '') return;
    const utterance = new SpeechSynthesisUtterance(text);
    const selected = voices.find(voice => voice.name === selectedVoice);
    if (selected) utterance.voice = selected;

    // Speak
    window.speechSynthesis.speak(utterance);

    // Save audio as a blob and generate URL for download
    // ⚠️ Web Speech API doesn't support saving audio directly
    // But we can simulate a downloadable text file as an example
    const blob = new Blob([text], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    setAudioUrl(url);
  };

  const handleSpeechToText = () => {
    if (!('webkitSpeechRecognition' in window)) {
      alert('Speech Recognition not supported in this browser.');
      return;
    }

    if (!isRecording) {
      const recognition = new window.webkitSpeechRecognition();
      recognition.continuous = false;
      recognition.interimResults = false;
      recognition.lang = 'en-US';

      recognition.onstart = () => setIsRecording(true);

      recognition.onresult = (event) => {
        const result = event.results[0][0].transcript;
        setTranscript(result);
        setIsRecording(false);
      };

      recognition.onerror = (event) => {
        console.error('Speech recognition error', event.error);
        setIsRecording(false);
      };

      recognition.onend = () => setIsRecording(false);

      recognition.start();
      recognitionRef.current = recognition;
    } else {
      recognitionRef.current?.stop();
      setIsRecording(false);
    }
  };

  const handleDownload = () => {
    if (!audioUrl) return;
    const link = document.createElement('a');
    link.href = audioUrl;
    link.download = 'output.txt';
    link.click();
  };

  return (
    <div className="app">
      <h1>Voice ↔ Text Converter</h1>

      <div>
        <button onClick={() => setMode('text-to-voice')} className={mode === 'text-to-voice' ? 'active' : ''}>
          Text to Voice
        </button>
        <button onClick={() => setMode('voice-to-text')} className={mode === 'voice-to-text' ? 'active' : ''}>
          Voice to Text
        </button>
      </div>

      {mode === 'text-to-voice' ? (
        <>
          <textarea
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder="Type your text here..."
          ></textarea>

          <div>
            <select value={selectedVoice} onChange={(e) => setSelectedVoice(e.target.value)}>
              {voices.map((voice, idx) => (
                <option key={idx} value={voice.name}>
                  {voice.name} ({voice.lang})
                </option>
              ))}
            </select>
          </div>

          <button onClick={handleTextToSpeech}>Speak</button>

          {audioUrl && (
            <button onClick={handleDownload} style={{ backgroundColor: '#007BFF' }}>
              Download Audio (as text)
            </button>
          )}
        </>
      ) : (
        <>
          <button onClick={handleSpeechToText}>
            {isRecording ? 'Stop Recording' : 'Start Speaking'}
          </button>
          <textarea value={transcript} readOnly placeholder="Speech to text output..."></textarea>
        </>
      )}
    </div>
  );
};

export default App;
