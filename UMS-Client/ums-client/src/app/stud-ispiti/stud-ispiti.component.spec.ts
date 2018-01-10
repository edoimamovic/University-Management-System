import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudIspitiComponent } from './stud-ispiti.component';

describe('StudIspitiComponent', () => {
  let component: StudIspitiComponent;
  let fixture: ComponentFixture<StudIspitiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudIspitiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudIspitiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
