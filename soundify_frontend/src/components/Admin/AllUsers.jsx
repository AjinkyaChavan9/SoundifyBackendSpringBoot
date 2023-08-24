import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'


function AllUsers() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = () => {
    axios.get('http://127.0.0.1:8080/api/admin/users')
      .then(response => {
        setUsers(response.data);
        console.log(response.data);
      })
      .catch(error => {
        console.error('Error fetching users:', error);
      });
  };

  const handleDelete = (userId) => {
    axios.delete(`http://127.0.0.1:8080/api/admin/user/${userId}`)
      .then(response => {
        console.log('User deleted:', response.data);
        fetchUsers(); // Refresh the user list after deletion
      })
      .catch(error => {
        console.error('Error deleting user:', error);
      });
  };

  return (
    <div className="all-users">
      <h2>All Users</h2>
      <div className="user-list">
        <table className="table">
          <thead>
            <tr>
              <th>Id</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Date Of Birth</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user, index) => (
              <tr key={index}>
                <td>{user.id}</td>
                <td>{user.firstName}</td>
                <td>{user.lastName}</td>
                <td>{user.email}</td>
                <td>{user.dateOfBirth}</td>
                <td>
                  <button className="btn waves-effect waves-light #e53935 red darken-1 btn-danger" onClick={() => handleDelete(user.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AllUsers;
