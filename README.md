# Device API

This API is used to manage devices in the system. It provides endpoints for creating, retrieving, updating, and deleting devices.

## Endpoints
- `POST /devices`: Create a new device.
- `GET /devices`: Retrieve a list of all devices.
- `GET /devices/{id}`: Retrieve a specific device by its ID.
- `PUT /devices/{id}`: Update a specific device by its ID.
- `DELETE /devices/{id}`: Delete a specific device by its ID.

## Request and Response Formats
### Create a Device
- **Request Body**:
```json
{
  "name": "Device Name",
  "brand": "Device Brand",
  "status": "AVAILABLE | IN_USE | IN_ACTIVE"
}
```
- **Response**:
```json
{
  "id": "Device ID",
  "name": "Device Name",
  "brand": "Device Brand",
  "status": "AVAILABLE | IN_USE | IN_ACTIVE",
  "creationTime": "11:17:48"
}
```
### Retrieve All Devices
- **Response**:
```json
[
  {
    "id": "Device ID",
    "name": "Device Name",
    "brand": "Device Brand",
    "status": "AVAILABLE | IN_USE | IN_ACTIVE",
    "creationTime": "11:17:48"
  },
  "..."
]
```
### Retrieve a Specific Device
- **Response**:
```json
{
  "id": "Device ID",
  "name": "Device Name",
  "brand": "Device Brand",
  "status": "AVAILABLE | IN_USE | IN_ACTIVE",
  "creationTime": "11:17:48"
}
```

### Update a Device
- **Request Body**:
```json
{
  "name": "Updated Device Name",
  "brand": "Updated Device Brand",
  "status": "AVAILABLE | IN_ACTIVE"
}
```

- **Response**:
```json
{
  "id": "Device ID",
  "name": "Updated Device Name",
  "brand": "Updated Device Brand",
  "status": "AVAILABLE | IN_ACTIVE",
  "creationTime": "11:17:48"
}
```

### Delete a Device
- **Response**:
```text
204 No Content
```

## Error Handling
- **400 Bad Request**: Returned when the request body is invalid or missing required fields.
- **404 Not Found**: Returned when a device with the specified ID does not exist.
- **500 Internal Server Error**: Returned when an unexpected error occurs on the server.
## Conclusion
This API provides a comprehensive set of endpoints for managing devices in the system. By following the request and response formats outlined above, you can easily integrate with the API to create, retrieve, update, and delete devices as needed. Always ensure to handle errors appropriately to maintain a smooth experience when using the API.
