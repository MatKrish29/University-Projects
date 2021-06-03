import { useRouteMatch } from "react-router-dom";
import { makeStyles } from "@material-ui/core";
import AddEmployee from "./AddEmployee";
import EmployeeDetails from "./EmployeeDetails";

const useStyles = makeStyles((theme) => ({
  employeeWrapper: {
    position: "absolute",
    width: "80%",
    height: "100vh",
    marginTop: "100px",
    marginLeft: "255px",
    zIndex: 3,
  },
}));

function Employees(props) {
  const classes = useStyles();

  let { url } = useRouteMatch();

  return (
    <div className={classes.employeeWrapper}>
      {url === "/dashboard/view-employee" ? (
        <EmployeeDetails />
      ) : (
        <AddEmployee />
      )}
    </div>
  );
}
export default Employees;
