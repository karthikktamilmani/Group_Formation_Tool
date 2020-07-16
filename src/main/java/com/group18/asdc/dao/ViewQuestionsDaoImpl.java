package com.group18.asdc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.group18.asdc.database.ConnectionManager;
import com.group18.asdc.entities.BasicQuestionData;
import com.group18.asdc.entities.QuestionMetaData;
import com.group18.asdc.entities.User;
import com.group18.asdc.util.QuestionManagerDataBaseQueries;

public class ViewQuestionsDaoImpl implements ViewQuestionsDao {

	Logger log = Logger.getLogger(ViewQuestionsDaoImpl.class.getName());

	@Override
	public List<QuestionMetaData> getAllQuestions(User currentUser) {

		List<QuestionMetaData> allQuestions = new ArrayList<QuestionMetaData>();
		
		try (Connection connection = ConnectionManager.getInstance().getDBConnection();
				PreparedStatement thePreparedStatement = connection
						.prepareStatement(QuestionManagerDataBaseQueries.GET_ALL_QUESTIONS.toString());){
			
			thePreparedStatement.setString(1, currentUser.getBannerId());
			ResultSet theResultSet = thePreparedStatement.executeQuery();
			QuestionMetaData theQuestionMetaData = null;
			BasicQuestionData theBasicQuestionData = null;
			while (theResultSet.next()) {
				theQuestionMetaData = new QuestionMetaData();
				theBasicQuestionData = new BasicQuestionData();
				theQuestionMetaData.setQuestionId(theResultSet.getInt(1));
				theBasicQuestionData.setQuestionTitle(theResultSet.getString(2));
				theBasicQuestionData.setQuestionText(theResultSet.getString(3));
				theQuestionMetaData.setCreationDateTime(theResultSet.getTimestamp(4));
				theBasicQuestionData.setQuestionType(theResultSet.getString(5));
				theQuestionMetaData.setBasicQuestionData(theBasicQuestionData);
				allQuestions.add(theQuestionMetaData);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE,
					"SQL Exception while getting all the question for user with id " + currentUser.getBannerId());
		} 
		return allQuestions;
	}

	@Override
	public List<QuestionMetaData> getAllQuestionsSortByDate(User currentUser) {

		List<QuestionMetaData> allQuestions = new ArrayList<QuestionMetaData>();
		
		try (Connection connection = ConnectionManager.getInstance().getDBConnection();
				PreparedStatement thePreparedStatement = connection
						.prepareStatement(QuestionManagerDataBaseQueries.GET_ALL_QUESTIONS_SORTEDBY_DATE.toString());){
			
			thePreparedStatement.setString(1, currentUser.getBannerId());
			ResultSet theResultSet = thePreparedStatement.executeQuery();
			
			QuestionMetaData theQuestionMetaData = null;
			BasicQuestionData theBasicQuestionData = null;
			
			while (theResultSet.next()) {
				theQuestionMetaData = new QuestionMetaData();
				theBasicQuestionData = new BasicQuestionData();
				theQuestionMetaData.setQuestionId(theResultSet.getInt(1));
				theBasicQuestionData.setQuestionTitle(theResultSet.getString(2));
				theBasicQuestionData.setQuestionText(theResultSet.getString(3));
				theQuestionMetaData.setCreationDateTime(theResultSet.getTimestamp(4));
				theBasicQuestionData.setQuestionType(theResultSet.getString(5));
				theQuestionMetaData.setBasicQuestionData(theBasicQuestionData);
				allQuestions.add(theQuestionMetaData);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL Exception while getting all the questions sorted by date for user with id "
					+ currentUser.getBannerId());
		} 
		return allQuestions;
	}

	@Override
	public List<QuestionMetaData> getAllQuestionsSortByTitle(User currentUser) {

		List<QuestionMetaData> allQuestionsSortByTitle = new ArrayList<QuestionMetaData>();
		
		try (Connection connection = ConnectionManager.getInstance().getDBConnection();
				PreparedStatement thePreparedStatement = connection
						.prepareStatement(QuestionManagerDataBaseQueries.GET_ALL_QUESTIONS_SORTEDBY_TITLE.toString());){
			
			thePreparedStatement.setString(1, currentUser.getBannerId());
			ResultSet theResultSet = thePreparedStatement.executeQuery();
			QuestionMetaData theQuestionMetaData = null;
			BasicQuestionData theBasicQuestionData = null;
			while (theResultSet.next()) {
				theQuestionMetaData = new QuestionMetaData();
				theBasicQuestionData = new BasicQuestionData();
				theQuestionMetaData.setQuestionId(theResultSet.getInt(1));
				theBasicQuestionData.setQuestionTitle(theResultSet.getString(2));
				theBasicQuestionData.setQuestionText(theResultSet.getString(3));
				theQuestionMetaData.setCreationDateTime(theResultSet.getTimestamp(4));
				theBasicQuestionData.setQuestionType(theResultSet.getString(5));
				theQuestionMetaData.setBasicQuestionData(theBasicQuestionData);
				allQuestionsSortByTitle.add(theQuestionMetaData);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL Exception while getting all the question sorted by title for user with id "
					+ currentUser.getBannerId());
		}
		return allQuestionsSortByTitle;
	}

	@Override
	public QuestionMetaData getQuestionById(int questionId) {

		QuestionMetaData theQuestionMetaData = null;
		BasicQuestionData thBasicQuestionData = null;
		
		try (Connection connection = ConnectionManager.getInstance().getDBConnection();
				PreparedStatement thePreparedStatement = connection
						.prepareStatement(QuestionManagerDataBaseQueries.GET_QUESTION_BY_ID.toString());){
			
			thePreparedStatement.setInt(1, questionId);
			ResultSet theResultSet = thePreparedStatement.executeQuery();
			while (theResultSet.next()) {
				theQuestionMetaData = new QuestionMetaData();
				thBasicQuestionData = new BasicQuestionData();
				theQuestionMetaData.setQuestionId(theResultSet.getInt(1));
				thBasicQuestionData.setQuestionTitle(theResultSet.getString(2));
				thBasicQuestionData.setQuestionText(theResultSet.getString(3));
				theQuestionMetaData.setCreationDateTime(theResultSet.getTimestamp(4));
				thBasicQuestionData.setQuestionType(theResultSet.getString(5));
				theQuestionMetaData.setBasicQuestionData(thBasicQuestionData);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL Exception while getting the question with id " + questionId);
		} 
		return theQuestionMetaData;
	}
}