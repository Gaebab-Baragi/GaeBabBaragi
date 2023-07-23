import React from 'react';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';
import FindIdForm from '../components/form/FindIdForm';

function FindIdPage() {
  return(
    <FindIdForm/>
  )
}

export default FindIdPage;