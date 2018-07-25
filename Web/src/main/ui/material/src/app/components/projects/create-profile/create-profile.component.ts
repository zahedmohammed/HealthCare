import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { MatStepper } from '@angular/material';
import { StepOneComponent } from '../step-one/step-one.component';
import { StepTwoComponent } from '../step-two/step-two.component';
import { StepThreeComponent } from '../step-three/step-three.component';


@Component({
  selector: 'create-profile-component',
  templateUrl: './create-profile.component.html',
  styleUrls: ['./create-profile.component.scss']
})
export class CreateProfileComponent implements OnInit {

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  @ViewChild('StepOneComponent') stepOneComponent: StepOneComponent;
    @ViewChild('StepTwoComponent') stepTwoComponent: StepTwoComponent;
    @ViewChild('StepTwoComponent') stepThreeComponent: StepThreeComponent;

    get frmStepOne() {
      return this.stepOneComponent ? this.stepOneComponent.frmStepOne : null;
   }

   get frmStepTwo() {
    return this.stepTwoComponent ? this.stepTwoComponent.frmStepTwo : null;
 }

 get frmStepThree() {
    return this.stepThreeComponent ? this.stepThreeComponent.frmStepThree : null;
 }

}
