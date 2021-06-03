import { ThemeProvider } from '@material-ui/core';
import theme from '../src/theme/index.js';
import GlobalStyles from './components/GlobalStyles';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";

import ProtectedRoute from './utils/ProtectedRoute.js';
import Dashboard from './layouts/dashboard/Dashboard.js';
import Login from './layouts/login/Login.js';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyles/>
      
      <Router>
        <div className='content-root' style={{width: "100%", height: "100vh"}}>
          <Switch>
            <Route path="/login">
              <Login />
            </Route>

            <ProtectedRoute path="/dashboard">
              <Dashboard />
            </ProtectedRoute>  

            <Route exact path="/">
              <Redirect exact from="/" to="dashboard" />
            </Route>

            <Route path="*">
              <Redirect from="/" to="dashboard" />
            </Route>

          </Switch>
        </div>
      </Router>

    </ThemeProvider>
  );
}
export default App;






