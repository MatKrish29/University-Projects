import { useRouteMatch } from "react-router-dom";
import { makeStyles } from "@material-ui/core";
import AddProject from "./AddProject";
import ProjectDetails from "./ProjectDetails";

const useStyles = makeStyles((theme) => ({
  projectWrapper: {
    width: "80%",
    height: "100vh",
    zIndex: 3,
    paddingTop: "100px",
    marginLeft: "255px",
  },
}));

function Projects() {
  const classes = useStyles();

  let { url } = useRouteMatch();

  return (
    <div className={classes.projectWrapper}>
      {url === "/dashboard/view-project" ? <ProjectDetails /> : <AddProject />}
    </div>
  );
}
export default Projects;
