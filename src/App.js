import { useState, useRef } from "react";
import "./App.css";

function App() {
  const inputRef = useRef(null);
  const [result, setResult] = useState(0);

  const handleOperation = (op) => {
    const inputValue = inputRef.current.value.trim();

    if (inputValue === "") {
      alert("Please enter a number");
      return;
    }

    const value = Number(inputValue);
    if (isNaN(value)) {
      alert("Invalid number");
      return;
    }

    switch (op) {
      case "add":
        setResult((prev) => prev + value);
        break;
      case "subtract":
        setResult((prev) => prev - value);
        break;
      case "multiply":
        setResult((prev) => prev * value);
        break;
      case "divide":
        if (value === 0) {
          alert("Cannot divide by zero");
          return;
        }
        setResult((prev) => prev / value);
        break;
      default:
        break;
    }
  };

  const resetInput = () => {
    inputRef.current.value = "";
  };

  const resetResult = () => {
    setResult(0);
  };

  return (
    <div className="App">
      <h1>Simplest Working Calculator</h1>
      <p>{result}</p>
      <input ref={inputRef} placeholder="Enter a number" />
      <div className="buttons">
        <button onClick={() => handleOperation("add")}>add</button>
        <button onClick={() => handleOperation("subtract")}>subtract</button>
        <button onClick={() => handleOperation("multiply")}>multiply</button>
        <button onClick={() => handleOperation("divide")}>divide</button>
        <button className="reset" onClick={resetInput}>reset input</button>
        <button className="reset" onClick={resetResult}>reset result</button>
      </div>
    </div>
  );
}

export default App;
