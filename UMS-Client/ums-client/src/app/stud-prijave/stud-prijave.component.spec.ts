import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudPrijaveComponent } from './stud-prijave.component';

describe('StudPrijaveComponent', () => {
  let component: StudPrijaveComponent;
  let fixture: ComponentFixture<StudPrijaveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudPrijaveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudPrijaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
