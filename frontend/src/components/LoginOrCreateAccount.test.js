import React from "react";
import ReactDOM from "react-dom";
import { fireEvent, getQueriesForElement } from "@testing-library/react";

import LoginOrCreateAccount from "./LoginOrCreateAccount";

test("should fetch data and change text within enterAsGuest button", () => {
  const div = document.createElement("div");
  ReactDOM.render(<LoginOrCreateAccount />, div);

  const { getByText } = getQueriesForElement(div);

  // at first check if the button has default text
  expect(getByText("Enter as a Guest")).not.toBeNull();

  // simulate clicking the button
  fireEvent.click(getByText("Enter as a Guest"));

  // check if text inside changed to OK
  expect(getByText("OK")).not.toBeNull();
});
