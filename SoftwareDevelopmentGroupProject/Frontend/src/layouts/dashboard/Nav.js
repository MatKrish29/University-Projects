import "../../layouts/dashboard/nav.css"
import Avatar from "../../assets/images/avatar.png";
import Eternity from "../../assets/images/eternity.png";

import { Link, useRouteMatch } from "react-router-dom";
import { makeStyles } from "@material-ui/core";




const useStyles = makeStyles((theme) => ({
    sideNavBar: {
        position: "absolute",
    },

}));




function Nav(props) {
    const classes = useStyles();
    let { url } = useRouteMatch();

    return (
        <div className={classes.sideNavBar}>
            <input type="checkbox" id="menu" />
            <nav style={{ zIndex: 4 }}>
                <label className="inertia-label" > <img src={Eternity} alt="img" />   INERTIA</label>

                <ul >

                    <li >
                        <div class="container">
                            <img src={Avatar} alt="Avatar" class="image" />
                            <div class="overlay">
                                <div class="text">Welcome Admin</div>
                            </div>
                        </div>
                        {/* <img src={Avatar} alt="img" />{props.username}<div class="overlay">
                        <div class="text">Hello World</div>
                    </div> */}
                    </li>

                </ul>

                <label for="menu" class="menu-bar">
                    <i class="fa fa-bars"></i>
                </label>
            </nav>

            <div class="side-menu" style={{ zIndex: 2 }}>
                <center>

                </center>
                <br />
                <Link to={'/'}><a href="/#"><i class="fa fa-home"></i><span>Dashboard</span></a></Link>
                <Link to={`${url}/view-employee`}><a href="/#"><i class="fa fa-users"></i><span>Employees</span></a></Link>
                <Link to={`${url}/add-employee`}><a href="/#"><i class="fa fa-user-plus"></i><span>Add Employee</span></a></Link>
                <Link to={`${url}/view-project`}><a href="/#"><i class="fa fa-list-alt"></i><span>Projects</span></a></Link>
                <Link to={`${url}/add-project`}><a href="/#"><i class="fa fa-calendar-plus"></i><span>Add Projects</span></a></Link>
                <Link to={`${url}/repo`}><a href="/#"><i class="fa fa-cog"></i><span>Repository</span></a></Link>
                <Link to={`${url}/email`}><a href="/#"><i class="fa fa-envelope"></i><span>Email</span></a></Link>
                <a href="/#" onClick={() => localStorage.removeItem("token")}><i class="fa fa-times" ></i><span>Sign out</span></a>
            </div>
        </div>
    );

}
export default Nav;
