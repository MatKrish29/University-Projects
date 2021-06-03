
import { makeStyles } from "@material-ui/core";
import axios from "axios"
import { useState, useEffect } from "react";

const useStyles = makeStyles((theme) => ({
  repoWrapper: {
    width: "80%",
    position: "absolute",
    marginTop: "120px",
    marginLeft: "270px",
    zIndex: -3,
  }
}));


const GetRepositories = () => {
  const [repos, setRepos] = useState([]);
  const API_URL = "https://api.github.com";

  const GITHUB_TOKEN = "0ee2e0cd74c44860f411f5bccc34a6ba0e1e6754";


  useEffect(() => {
    const config = {
      headers: {
        Authorization: `token ${GITHUB_TOKEN}`,
        Accept: `application/vnd.github.v3+json`,
      },
    };
    axios
      .get(API_URL + "/user/repos", config)
      .then((response) => {
        setRepos(response.data);
      })
      .catch((error) => {
        if (error.response) {
          // Request made and server responded
          console.log("response_data : " + error.response.data);
          console.log("response_status : " + error.response.status);
          console.log("response_headers : " + error.response.headers);

          if (error.response.status === 400) {
            console.log("Invalid credientials");
          }
        } else if (error.request) {
          console.log("server is offline");
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log("Something went wrong. Please try again later.");
        }
      });
  }, [repos]);

  return (
    <div className="row ">
      <table class="table">
        <thead class="thead-dark">
          <tr>
            <th scope="col">Repository Name</th>
            <th scope="col">Contributors</th>
            <th scope="col">Created at</th>
            <th scope="col">Url</th>
          </tr>
        </thead>
        <tbody>
          {repos.map(data => (
            <tr key={data.id}>
              <th scope="row">{data.full_name}</th>
              <td>{data.contributors_url}</td>
              <td>{data.created_at}</td>
              <td><a href={data.html_url}>{data.html_url}</a></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );

}


function Repo() {
  const classes = useStyles();

  return (
    <div className={classes.repoWrapper} style={{ zIndex: 3 }}>
      <h4>Repositories</h4>
      <GetRepositories />
    </div>
  );
}
export default Repo;







