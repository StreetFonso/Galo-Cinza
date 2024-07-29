SCRIPT_START
{
	LVAR_INT scplayer iGymLS iGymSF iGymLV iGaloCinza iRandom

	GET_PLAYER_CHAR 0 scplayer

	main_loop:
	WAIT 0
	SET_CHAR_ANIM_SPEED scplayer "FightD_M" 1.67 //now it's not a caress
	IF IS_CHAR_FIGHTING scplayer
		IF iGymLS = FALSE
			CLEO_CALL ReadGlobalVar 0 (8153)(iGymLS) // gym_ls_defeated
		ELSE
			CLEO_CALL WriteGlobalVar 0 (8156 1)() // player_been_taught_moveLS
		ENDIF
		IF iGymSF = FALSE
			CLEO_CALL ReadGlobalVar 0 (8154)(iGymSF) // gym_sf_defeated
		ELSE
			CLEO_CALL WriteGlobalVar 0 (8155 1)() // player_been_taught_moveSF
		ENDIF
		IF iGymLV = FALSE
			CLEO_CALL ReadGlobalVar 0 (8158)(iGymLV) // gym_lv_defeated
		ELSE
			CLEO_CALL WriteGlobalVar 0 (8157 1)() // player_been_taught_moveLV
		ENDIF
		WHILE IS_CHAR_FIGHTING scplayer
		AND iGaloCinza = FALSE
			WAIT 0
			SET_CHAR_ANIM_SPEED scplayer "FightD_M" 1.67 //now it's not a caress
			IF iGymLS = TRUE
			AND iGymSF = FALSE
			AND iGymLV = FALSE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer 5 6 //Boxing
			ENDIF
			IF iGymLS = FALSE
			AND iGymSF = TRUE
			AND iGymLV = FALSE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer 6 6 //Kung Fu
			ENDIF
			IF iGymLS = FALSE
			AND iGymSF = FALSE
			AND iGymLV = TRUE
				GIVE_MELEE_ATTACK_TO_CHAR scplayer 7 6 //Kickboxing
			ENDIF
			IF iGymLS = TRUE
			AND iGymSF = TRUE
			AND iGymLV = FALSE
				GENERATE_RANDOM_INT_IN_RANGE 5 7 iRandom
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iRandom 6 //Boxing or Kung Fu
			ENDIF
			IF iGymLS = FALSE
			AND iGymSF = TRUE
			AND iGymLV = TRUE
				GENERATE_RANDOM_INT_IN_RANGE 6 8 iRandom
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iRandom 6 //Kung Fu or Kickboxing
			ENDIF
			IF iGymLS = TRUE
			AND iGymSF = FALSE
			AND iGymLV = TRUE
				GOSUB randomize57 //Kung Fu not learned.
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iRandom 6 //Boxing or Kickboxing
			ENDIF
			IF iGymLS = TRUE
			AND iGymSF = TRUE
			AND iGymLV = TRUE
				GENERATE_RANDOM_INT_IN_RANGE 5 8 iRandom
				GIVE_MELEE_ATTACK_TO_CHAR scplayer iRandom 6 //Boxing, Kung Fu or Kickboxing
			ENDIF
			IF iGymLS = FALSE
			AND iGymSF = FALSE
			AND iGymLV = FALSE
			GIVE_MELEE_ATTACK_TO_CHAR scplayer 15 6 //Punch and kick //This will be ignored if scplayer learn any new moves.
			ENDIF
		ENDWHILE
	ENDIF

	IF iGaloCinza = TRUE
		GENERATE_RANDOM_INT_IN_RANGE 5 8 iRandom
		GIVE_MELEE_ATTACK_TO_CHAR scplayer iRandom 6 //Cheat
	ENDIF

	IF TEST_CHEAT GALOCINZA //Galo cinza de pescoço fino tem fama de grande brigador em rinhas de galo.
	OR TEST_CHEAT GRAYROOSTER //Thin-necked gray rooster is famous for being a great fighter in cockfights.
		IF iGaloCinza = FALSE
			iGaloCinza = TRUE
			PRINT_HELP CHEAT1 //Cheat activated
		ELSE
			iGaloCinza = FALSE
			PRINT_HELP CHEAT8 //Cheat deactivated
		ENDIF
	ENDIF

	GOTO main_loop

	randomize57:
	GENERATE_RANDOM_INT_IN_RANGE 5 8 iRandom
	IF iRandom = 6 //Kung Fu not learned.
		GOTO randomize57
	ENDIF
	RETURN

}

SCRIPT_END

{
	LVAR_INT var value //In
	LVAR_INT scriptSpace finalOffset

	WriteGlobalVar:
	READ_MEMORY 0x00468D5E 4 1 (scriptSpace)
	finalOffset = var * 4
	finalOffset += scriptSpace
	WRITE_MEMORY finalOffset 4 (value) FALSE
	CLEO_RETURN 0 ()
}

{
	LVAR_INT var //In
	LVAR_INT value scriptSpace finalOffset

	ReadGlobalVar:
	READ_MEMORY 0x00468D5E 4 1 (scriptSpace)
	finalOffset = var * 4
	finalOffset += scriptSpace
	READ_MEMORY finalOffset 4 FALSE (value)
	CLEO_RETURN 0 (value)
}