import {
  Switch,
  useRouteMatch
} from "react-router-dom";
import axios from "axios";
import ProtectedRoute from "../../utils/ProtectedRoute";
import Employees from "../employees/Employees";
import Projects from "../projects/Projects";
import Repo from "../repo/Repo";
import Email from "../email/email";

import 'react-pro-sidebar/dist/css/styles.css';
import { makeStyles } from "@material-ui/core";
import Nav from "./Nav";
import DashboardContent from "../dashboardContent/DashboardContent";


const useStyles = makeStyles((theme) => ({
  container: {
    position: "relative",
    margin: 0,
    padding: 0,
  },

}));
function getUserName() {
  const token = localStorage.getItem("token");
  const config = {
    headers: { Authorization: `Token ${token}` },
  };
  var text = "";
  if (text === "") {
    axios
      .get("http://127.0.0.1:8000/user/", config)
      .then((response) => {

        text = response.data[0].username;
        console.log(text);
      });
  }
  return text;
}
function Dashboard() {
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Nav username={getUserName()} />
      <NestedRoutes />
    </div>
  );
}
export default Dashboard;

function NestedRoutes() {

  let { path } = useRouteMatch();

  return (
    <Switch>

      <ProtectedRoute exact path={path}>
        <DashboardContent />

      </ProtectedRoute>

      <ProtectedRoute path={`${path}/view-employee`}>
        <Employees />
      </ProtectedRoute>

      <ProtectedRoute path={`${path}/add-employee`}>
        <Employees />
      </ProtectedRoute>

      <ProtectedRoute path={`${path}/view-project`}>
        <Projects />
      </ProtectedRoute>

      <ProtectedRoute path={`${path}/add-project`}>
        <Projects />
      </ProtectedRoute>

      <ProtectedRoute path={`${path}/repo`}>
        <Repo />
      </ProtectedRoute>

      <ProtectedRoute path={`${path}/email`}>
        <Email />
      </ProtectedRoute>
    </Switch>
  );
}