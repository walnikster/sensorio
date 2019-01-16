import { SensorsComponent } from './sensors/sensors.component'
import { SensorComponent } from './sensor/sensor.component'
import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { NotImplementedComponent } from './not-implemented/not-implemented.component'

const routes: Routes = [
  { path: '', component: SensorsComponent },
  { path: 'home', component: SensorsComponent },
  { path: 'sensors', component: SensorsComponent },
  { path: 'sensor', component: SensorComponent },
  { path: '**', component: NotImplementedComponent }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
