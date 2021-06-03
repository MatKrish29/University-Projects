import {Link,useRouteMatch} from "react-router-dom";
import { makeStyles } from "@material-ui/core";


const useStyles = makeStyles((theme) => ({
     sideNavBar: {
        position: "absolute", 
        width: "100%", 
        height: "100vh", 
        zIndex: 3, 
        paddingTop: "50px"
    },

    li: {
        padding: '15px'
    } 


  }));

  function SideNavBar() {
    const classes = useStyles();
    let { url } = useRouteMatch();

    return(
        <div className={classes.sideNavBar}>

            <h2 style={{paddingLeft: '15px'}}>Menu</h2>
            <ul className={classes.ul} style={{listStyle: "none"}}>
                <li className={classes.li}>
                    <Link to={'/'}>Dashboard</Link>
                </li>

                <li className={classes.li}>
                    <Link to={`${url}/view-employee`}>Employees</Link>
                </li>

                <li className={classes.li}>
                    <Link to={`${url}/add-employee`}>Add Employee</Link>
                </li>

                <li className={classes.li}>
                    <Link to={`${url}/view-project`}>Projects</Link>
                </li>

                <li className={classes.li}>
                    <Link to={`${url}/add-project`}>Add Projects</Link>
                </li>

                <li className={classes.li}>
                    <Link to={`${url}/repo`}>Repositories</Link>
                </li>

                <li className={classes.li}>
                    <a href='/#' onClick={() => {localStorage.removeItem("token")}}>Sign out</a>
                </li>
            </ul> 

        </div>
    );
   
}
export default SideNavBar;