# statemachine

This library is a sort of hybrid implementation of the state pattern and a state machine.  The intention is to provide a simple way of reacting to events based on the current state of a domain object. 

1. Create a state machine model:
```java
new StateMachineModelBuilder<StateEnum, DomainObject>()
  .states(StateEnum.class)
  .initialState(StateEnum.START_STATE)
  .transition()
    .from(StateEnum.START_STATE)
    .to(StateEnum.END_STATE)
    .on(ExampleEvent.class)
    .withoutCondition()
  .action()
    .in(StateEnum.START_STATE)
    .on(ExampleEvent.class)
    .doAction((currentState, appCtx, event) -> 
      System.out.println("Doing action in " + currentState + " on " + event + " event.")
    )
  .action()
    .exiting(StateEnum.START_STATE)
    .on(ExampleEvent.class)
    .doAction((currentState, appCtx, event) -> 
      System.out.println("Doing action while exiting " + currentState + " on " + event + " event.")
   )
  .action()
    .entering(StateEnum.END_STATE)
    .on(ExampleEvent.class)
    .doAction((currentState, appCtx, event) -> 
      System.out.println("Doing action while entering " + currentState + " on " + event + " event.")
    )
  .build();

```

2. Create a state machine out of the state machine model for some domain object:
```java
StateMachine<StateEnum, DomainObject> stateMachine = new StateMachine<>(domainObject, stateMachineModel);
```

3. Send events (that can be any object) to the state machine to execute actions and transitions based on the current state:
```java
stateMachine.sendEvent(new ExampleEvent());
```
```
##### Console Output #####
Doing action in START_STATE on ExampleEvent event.
Doing action while exiting START_STATE on ExampleEvent event.
Doing action while entering END_STATE on ExampleEvent event.
```

