import { Component,OnInit } from '@angular/core';

@Component({
  selector: 'my-app-layout',
  styleUrls: ['./layout.component.scss'],
  templateUrl: './layout.component.html',
})
export class LayoutComponent  implements OnInit {
 ngOnInit() {

    this.connect();
  }
messages = [
    {
      avatar: 'http://stg1.fxlabs.io:8083/static/d96c2a5d/images/16x16/red.png',
      from: 'Min Li Chan',
      subject: 'Brunch this weekend?',
      content: "I'll be in your neighborhood doing errands"
    }, {
      avatar: 'http://stg1.fxlabs.io:8083/static/d96c2a5d/images/16x16/blue.png',
      from: 'Bella Smith',
      subject: 'Hi there',
      content: 'Cras sit amet nibh libero, in gravida nulla.'
    }, {
      avatar: 'http://stg1.fxlabs.io:8083/static/d96c2a5d/images/16x16/red.png',
      from: 'Sophia Doe',
      subject: 'Wanna hang out tonight?',
      content: 'Nulla vel metus scelerisque ante sollicitudin commodo.'
    }, {
      avatar: 'http://stg1.fxlabs.io:8083/static/d96c2a5d/images/16x16/blue.png',
      from: 'Luna Doe',
      subject: 'See you then',
      content: 'Cras purus Donec lacinia congue felis in faucibus.'
    }
  ];
  connect(): void {
    let source = new EventSource('/api/v1/events/register');
    source.addEventListener('message', message => {
        let event = JSON.parse(message.data);
this.messages=[];
for(var i=0;i<event.length;i++){
var e=event[i];
var message:any={
      avatar: 'http://stg1.fxlabs.io:8083/static/d96c2a5d/images/16x16/red.png',
      from: 'Min Li Chan',
      subject: e.data

    };
this.messages.push(message);

}
      //  console.log(event);
        // TODO - display & update events in the Tasks window.
    });
  }
}
