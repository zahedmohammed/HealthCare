import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreateProfileComponent } from '../create-profile/create-profile.component';

@Component({
  selector: 'step-three-component',
  templateUrl: './step-three.component.html',
  styleUrls: ['./step-three.component.scss']
})
export class StepThreeComponent implements OnInit {
  frmStepThree: FormGroup;
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.frmStepThree = this.formBuilder.group({
      age: ['', Validators.required]
  });

  }

}
