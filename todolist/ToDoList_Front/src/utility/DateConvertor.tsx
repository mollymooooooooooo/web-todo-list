
export class DateConvertor{
	static GetDateWithoutTime(date: Date | undefined){

		if (date == undefined || isNaN(date.getTime())){
			return "";
		}
		
		const day = String(date.getDate()).padStart(2, '0');
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const year = date.getFullYear();

		const finalDate = `${day}.${month}.${year}`

		return finalDate;
	}
	static GetDateWithTime(date: Date){

		if (date == undefined){
			return "";
		}

		const day = String(date.getDate()).padStart(2, '0');
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const year = date.getFullYear();
		const hours = String(date.getHours()).padStart(2, '0');
		const minutes = String(date.getMinutes()).padStart(2, '0');

		const finalDate = `${day}.${month}.${year}/${hours}:${minutes}`

		return finalDate;
	}
	static GetDateWithoutTimeISOFormat(date: Date | undefined){

		if (date == undefined || isNaN(date.getTime())){
			return "";
		}

		const day = String(date.getDate()).padStart(2, '0');
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const year = date.getFullYear();


		const finalDate = `${year}-${month}-${day}`

		return finalDate;
	}
}