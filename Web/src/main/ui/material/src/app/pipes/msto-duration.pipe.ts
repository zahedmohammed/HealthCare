import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'mstoDuration'
})
export class MstoDurationPipe implements PipeTransform {

  transform(value: number, args?: any): any {
    if(!value) value = 0;
    if(isNaN(value)) value = 0;
    let precision: number = 0;
    let seconds: number = (value/1000);
    let minutes: number = seconds/60;
    seconds = seconds%60;
    let hours: number = minutes/60;
    minutes = minutes%60;

    return hours.toFixed(precision) + ':' + minutes.toFixed(precision) + ':' + seconds.toFixed(precision);
  }

}
