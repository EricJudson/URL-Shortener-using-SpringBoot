import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');

  const handleShorten = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/shorten', null, {
      params: { url: longUrl },
      headers: {
        'Content-Type': 'application/json'
      }
    });
    setShortUrl(response.data);
  } catch (error) {
    console.error('Error shortening URL:', error);
    alert('Failed to shorten URL.');
  }
};


  return (
    <div style={styles.container}>
      <h1>URL Shortener</h1>
      <input
        type="text"
        placeholder="Enter long URL"
        value={longUrl}
        onChange={(e) => setLongUrl(e.target.value)}
        style={styles.input}
      />
      <button onClick={handleShorten} style={styles.button}>
        Shorten URL
      </button>
      {shortUrl && (
        <div style={styles.result}>
          <p>Shortened URL:</p>
          <a href={shortUrl} target="_blank" rel="noopener noreferrer">
            {shortUrl}
          </a>
        </div>
      )}
    </div>
  );
}

const styles = {
  container: {
    textAlign: 'center',
    padding: '50px',
    fontFamily: 'Arial'
  },
  input: {
    width: '300px',
    padding: '10px',
    fontSize: '16px'
  },
  button: {
    marginLeft: '10px',
    padding: '10px 20px',
    fontSize: '16px',
    cursor: 'pointer'
  },
  result: {
    marginTop: '20px'
  }
};

export default App;
