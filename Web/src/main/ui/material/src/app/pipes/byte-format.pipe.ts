import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'byteFormat'
})
export class ByteFormatPipe implements PipeTransform {

  transform(value: number, args?: any): any {
    let precision: number = 1;
    if (!isFinite(value)) return '-';
    if (value == 0) value = 1;
	if (typeof precision === 'undefined') precision = 1;
	var units = ['B', 'kB', 'MB', 'GB', 'TB', 'PB'],
	number = Math.floor(Math.log(value) / Math.log(1024));
	return (value / Math.pow(1024, Math.floor(number))).toFixed(precision) +  ' ' + units[number];
  }

}
