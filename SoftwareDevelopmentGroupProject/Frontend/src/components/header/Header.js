import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
   header: {
        position:"absolute",
        height: "50px",
        width: "100%", 
        backgroundColor: "#999", 
        zIndex: 4,
    },   
  }));
  

  function Header() {
    const classes = useStyles();

    return(
        <div className={classes.same}>

            
        </div>
    );
   
}
export default Header;