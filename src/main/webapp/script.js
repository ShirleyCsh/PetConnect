// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
async function showPets() {
    const responseFromServer = await fetch('/home');
    const textFromResponse = await responseFromServer.json();

    for(let i=0; i<3; i++)
    {
        //Getting the id of each card
        let picID = "pic"+ i;
        let petTypeID = "petType" + i;
        let breedID = "breed" + i;
        let ageID = "age" + i;
        let locationID = "location" + i;
        let nameID = "name" + i;
        let emailID = "email" + i;

        //Update each card in the HTML
        document.getElementById(picID).src = "data:imagejpeg;base64," + textFromResponse[i].PIC;
        document.getElementById(petTypeID).innerHTML = textFromResponse[i].PET_TYPE;
        document.getElementById(breedID).innerHTML = "Breed: " + textFromResponse[i].BREED;
        document.getElementById(ageID).innerHTML = "Age: " + textFromResponse[i].AGE;
        document.getElementById(locationID).innerHTML = "Location: " + textFromResponse[i].LOCATION;
        document.getElementById(nameID).innerHTML = "Name: " + textFromResponse[i].NAME;
        document.getElementById(emailID).innerHTML = "Email: " + textFromResponse[i].EMAIL;

    }
}