import React from "react";

export type ValidationError = { fieldName: string, message: string }
const ErrorsList = ({ errorsList, fieldName }: { errorsList: ValidationError[], fieldName: string}) => (
  <>
  { errorsList.filter((x: ValidationError) : boolean => x["fieldName"] === fieldName).length !== 0 &&
  <ul className="p-4 mt-2 text-sm text-red-700 bg-red-100 rounded-lg dark:bg-red-200 dark:text-red-800" role="alert">
  {
    errorsList.filter(x => x["fieldName"] === fieldName).map(item => (<li key={item.message}>{item.message}</li>))
  }
  </ul>
  }
  </>
);

export default ErrorsList;
