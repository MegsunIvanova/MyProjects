const BASE_URL = '/api/my-vehicles';
const VEHICLE_HREF = '/vehicle/details/';

document.addEventListener("DOMContentLoaded", myVehicles)

function myVehicles() {
    const myVehiclesDropDown = document.getElementById("myVehicles");
    if (myVehiclesDropDown) {
        fetch(BASE_URL)
            .then(res => res.json())
            .then(data => {
                const vehicles = Object.values(data);

                vehicles.forEach((vehicle) => {

                    const aElement = document.createElement("a");
                    console.log(vehicle.title);
                    console.log(vehicle.uuid);
                    aElement.textContent = vehicle.title;
                    aElement.classList.add('dropdown-item', 'fs-5');
                    aElement.href = `${VEHICLE_HREF}${vehicle.uuid}`;

                    myVehiclesDropDown.appendChild(aElement);
                })
            })
    }
}

