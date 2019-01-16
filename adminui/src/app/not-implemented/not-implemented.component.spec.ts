import { element } from 'protractor'
import { async, ComponentFixture, TestBed } from '@angular/core/testing'

import { NotImplementedComponent } from './not-implemented.component'

describe('NotImplementedComponent', () => {
  let component: NotImplementedComponent
  let fixture: ComponentFixture<NotImplementedComponent>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NotImplementedComponent]
    }).compileComponents()
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(NotImplementedComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })

  it('not implementd', () => {
    const fixture = TestBed.createComponent(NotImplementedComponent)
    fixture.detectChanges()
    const compiled = fixture.debugElement.nativeElement
    expect(compiled.querySelector('p').textContent).toContain('not implemented!')
  })
})
