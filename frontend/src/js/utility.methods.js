export function handleServiceError(error) {
    if (error.response) {
        return `Failed to update data at endpoint. Response was: ${error.response.data.message}`
    } else if (error.request) {
        return "Failed to connect to server.";
    } else {
        return "Something went really wrong.";
    }
}