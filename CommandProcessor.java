import java.util.*;
/*
Lauren M DiGregorio
	  April 19, 2015
	  
	  For the final project
	  This class does the actual processing of commands for the user
	  
	  Class Variables
	  	commandRegistry
	  		holds all available commands that can be called. it is a Map<String Command>
	  	
	  	noSuchCommand
	  		A Command object that holds a CommandError object
	  	
	  	nothingEnteredCommand
	  		A command object that holds a CommandNone object
	  	
		
	  Constructors
	  	
	  	CommandProcessor(Command noSuchCommand, Command nothingEnteredCommand)
	  		sets noSUchCommand and nothingEnteredCommand, initalizes all commands that can be called in hash map
	
	   Methods
			public Commands[] getAllCommands()
				returns all commans
			
			public void regiater(Command command)
				adds command to registry
			
			public void runCommand(String commandText)
				Calls the command to be performed
				
			

*/
public class CommandProcessor 
{
	private Map<String, Command> 	commandRegistry; 		//holds all available commands that can be called. it is a Map<String Command>
	private Command					noSuchCommand; 			//A Command object that holds a CommandError object
	private Command 				nothingEnteredCommand; 	//A command object that holds a CommandNone object
	
	public CommandProcessor(Command noSuchCommand, Command nothingEnteredCommand)
	{
		//sets noSUchCommand and nothingEnteredCommand, initalizes all commands that can be called in hash map
		Command begin;
		if( noSuchCommand == null )			{ throw new IllegalArgumentException("CommandProcessor, Cannot pass 1st parameter as null.  Try again.");}
		if( nothingEnteredCommand == null )	{ throw new IllegalArgumentException("CommandProcessor, Cannot pass 1st parameter as null.  Try again.");}
		
		this.noSuchCommand = noSuchCommand;
		this.nothingEnteredCommand = nothingEnteredCommand;
		
		commandRegistry = new HashMap();
		commandRegistry.put("", this.noSuchCommand);
		commandRegistry.put(nothingEnteredCommand.getCommandName(), this.nothingEnteredCommand);
		
	}

	public Command[] getAllCommands()
	{
		//returns all commands
		Iterator it = commandRegistry.entrySet().iterator();
		Command[] commands;
		commands = new Command[commandRegistry.size()];
		
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}
		
		return commands;
		
	}
	
	public void register(Command command)
	{
		//adds command to registry
		String commandName;
		commandName = command.getCommandName();
		commandRegistry.put(commandName, command);
	}
	
	public void runCommand(String commandText)
	{
		//Calls the command to be performed
		Command command;
		
		if(this.commandRegistry.get(commandText) == null )	{ throw new IllegalArgumentException("Command not found"); }
		else												
		{ 
			command = this.commandRegistry.get(commandText);
			command.run();
		}//end else
		
	}
	public Command getCommand(String command)
	{
		//returns a command object
		Command result;
		String forEditing;
		String [] commandParamters;
		String	commandName;
		String	parameters;
		
		forEditing 			= command.trim();
		commandParamters 	= new String[3];
				//index one = commandName
				//index two = parameters
		
		commandName 		= "";
		parameters			= "";
		
		System.out.println("command to be edited "+forEditing.indexOf("Find"));
		
		if( forEditing.indexOf("Find") >= 0 || forEditing.indexOf("Get") >= 0 )
		{
			//then we want to parse the string and set the parameters if they exist
			if(forEditing.indexOf(" ") >= 0)
			{
				//means there are parameters
				commandParamters = forEditing.split(" ", 2);
				System.out.println(commandParamters.length);
				System.out.println(commandParamters[0]);
				System.out.println(commandParamters[1]);
				
				commandName = commandParamters[0];
				parameters	= commandParamters[1];
				
				System.out.println("Command Name "+forEditing);
				System.out.println("parameters "+forEditing);
				
			}
		}
		
		
		
		if( commandExists(commandName) )
		{
			
			result = this.commandRegistry.get(commandName);
			result.setParameters(parameters);
		}
		else
		{
			result = this.noSuchCommand;
		}
		System.out.println("*************I am returning command: " +result.getCommandName());
		System.out.println("************I am returning with parameters: " +result.getParameters());
		return result;
		
	}
	
	public boolean commandExists(String commandName)
	{
		boolean result;
		
		if(this.commandRegistry.get(commandName) == null )	{ result = false; }
		else												{ result = true;  }
		
		return result;
	}
}//end CommandProcessor
