async function fetchData() {
	try {
		const response = await fetch(
			"http://192.168.43.86:8080/api/v1/sms/get_all_sms"
		);
		const data = await response.json();
		return data;
	} catch (error) {
		console.error(error);
	}
}

async function displayData(data) {
	const container = document.getElementById("users-list");
	const allNumbersResponse = await fetch(
		"http://192.168.43.86:8080/api/v1/sms/get_all_phones"
	);
	const allNumbers = await allNumbersResponse.json();

	allNumbers.forEach((number) => {
		const dataList = document.createElement("button");
		dataList.className = "user-btn";

		const numbers = document.createElement("h3");
		numbers.className = "head3";
		numbers.innerHTML = `<h3 class="head3"><b>${number}</b><h3>`;

		dataList.appendChild(numbers);
		container.appendChild(dataList);

		dataList.addEventListener("click", () => {
			const phoneNumber = document.querySelector("#selected-user-info");
			phoneNumber.innerHTML = `<h2 class="head2"><b>${number}</b></h2>`;
			const phoneText = document.querySelector("#user-messages");
			const messagesForNumber = data.filter(
				(item) => item.receiverPhoneNumber === number
			);

			console.log(messagesForNumber);

			phoneText.innerHTML = messagesForNumber
				.map(
					(item) =>
						`<div class="message"><div class="message-top">${item.senderPhoneNumber}</div><p class="message-text">${item.message}</p><p class="message-date">-</p></div>`
				)
				.join("");
			selectedPhoneNumber = number;
		});
	});

	const sendSms = document.querySelector("#sms-input-text");
	const SendFormSMS = document.getElementById("send-sms-form");
	SendFormSMS.onsubmit = async (e) => {
		e.preventDefault();

		const formData = new FormData();
		formData.append("phoneNumber", selectedPhoneNumber);
		formData.append("message", sendSms.value.trim());

		console.log(selectedPhoneNumber);

		const response = await fetch(
			"http://192.168.43.86:8080/api/v1/sms/send_sms",
			{
				method: "POST",
				body: formData,
			}
		);

		if (response.status === 200) {
			console.log("Send SMS Success");
			sendSms.value = "";
		} else {
			console.log("Send SMS Failed");
		}
	};
}

const updateDataBtn = document.getElementById("update-data-btn");
updateDataBtn.addEventListener("click", async () => {
	data.value = "";
	const data = await fetchData();
	displayData(data);
});

let selectedPhoneNumber;

fetchData()
	.then((data) => displayData(data))
	.catch((error) => console.error(error));
