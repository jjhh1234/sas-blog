import { Login, SignUp, Main, Post, Write, MyPage} from './Page';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import './App.css';

function App() {
  return (
    <Router>
      <Routes>
      <Route path="/" element={<SignUp />}/>
        <Route path="SignUp" element={<SignUp />}/>
        <Route path="Login" element={<Login />}/>
        <Route path="Main" element={<Main />}/>
        <Route path="Post" element={<Post />}/>
        <Route path="Write" element={<Write />}/>
        <Route path="MyPage" element={<MyPage />}/>
      </ Routes>
   </ Router>
  );
}

export default App;
