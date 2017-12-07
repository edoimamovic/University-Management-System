import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IspitiPregledComponent } from './ispiti-pregled.component';

describe('IspitiPregledComponent', () => {
  let component: IspitiPregledComponent;
  let fixture: ComponentFixture<IspitiPregledComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IspitiPregledComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IspitiPregledComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
